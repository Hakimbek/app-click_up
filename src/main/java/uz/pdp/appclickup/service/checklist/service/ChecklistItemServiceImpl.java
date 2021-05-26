package uz.pdp.appclickup.service.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.task.CheckListItem;
import uz.pdp.appclickup.entity.task.Checklist;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ChecklistItemDTO;
import uz.pdp.appclickup.repository.ChecklistItemRepository;
import uz.pdp.appclickup.repository.ChecklistRepository;
import uz.pdp.appclickup.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChecklistItemServiceImpl implements ChecklistItemService {
    @Autowired
    ChecklistItemRepository checklistItemRepository;
    @Autowired
    ChecklistRepository checklistRepository;
    @Autowired
    UserRepository userRepository;


    /**
     * ADD CHECKLIST ITEM
     *
     * @param checklistId      UUID
     * @param checklistItemDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addChecklistItem(UUID checklistId, ChecklistItemDTO checklistItemDTO) {
        if (checklistItemRepository.existsByNameAndChecklistId(checklistItemDTO.getName(), checklistId)) {
            return new ApiResponse("Checklist already exist", false);
        }

        Optional<Checklist> optionalChecklist = checklistRepository.findById(checklistId);
        if (!optionalChecklist.isPresent()) {
            return new ApiResponse("Checklist not found", false);
        }
        Checklist checklist = optionalChecklist.get();

        User user = null;
        if (checklistItemDTO.getAssignUserId() != null) {
            Optional<User> optionalUser = userRepository.findById(checklistItemDTO.getAssignUserId());
            if (!optionalUser.isPresent()) {
                return new ApiResponse("User not found", false);
            }
            user = optionalUser.get();
        }

        checklistItemRepository.save(new CheckListItem(
                checklistItemDTO.getName(),
                checklist,
                checklistItemDTO.getResolved(),
                user
        ));

        return new ApiResponse("Checklist created", true);
    }


    /**
     * DELETE CHECKLIST ITEM
     *
     * @param checklistItemId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteChecklistItem(UUID checklistItemId) {
        try {
            checklistItemRepository.deleteById(checklistItemId);
            return new ApiResponse("Checklist Item deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL CHECKLISTS ITEM
     *
     * @param checklistId UUID
     * @return CHECKLIST ITEMS LIST
     */
    @Override
    public List<CheckListItem> getAllChecklistItem(UUID checklistId) {
        Optional<Checklist> optionalChecklist = checklistRepository.findById(checklistId);
        if (!optionalChecklist.isPresent()) {
            return new ArrayList<>();
        }

        return checklistItemRepository.findAllByChecklistId(checklistId);
    }


    /**
     * EDIT CHECKLIST ITEM
     *
     * @param checklistItemId  UUID
     * @param checklistItemDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editChecklistItem(UUID checklistItemId, ChecklistItemDTO checklistItemDTO) {
        if (checklistItemRepository.existsByNameAndChecklistId(checklistItemDTO.getName(), checklistItemId)) {
            return new ApiResponse("Checklist Item already exist", false);
        }

        Optional<User> optionalUser = userRepository.findById(checklistItemDTO.getAssignUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();

        Optional<CheckListItem> optionalCheckListItem = checklistItemRepository.findById(checklistItemId);
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("Checklist Item not found", false);
        }
        CheckListItem checkListItem = optionalCheckListItem.get();
        checkListItem.setName(checklistItemDTO.getName());
        checkListItem.setResolved(checklistItemDTO.getResolved());
        checkListItem.setAssignedUser(user);
        checklistItemRepository.save(checkListItem);

        return new ApiResponse("Checklist Item edited", true);
    }
}
