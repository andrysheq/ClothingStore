package com.web.auction.controllers;

import com.web.auction.data.RoleRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.Lot;
import com.web.auction.models.LotForm;
import com.web.auction.models.Role;
import com.web.auction.models.User;
import com.web.auction.security.RegistrationForm;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import static com.web.auction.models.LotForm.getLotFormWithLot;

@Component
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin-panel")
public class AdminPanelController {
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    public AdminPanelController (UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public String adminPanel (Model model){

        List<User> users = userRepo.findAll();

        model.addAttribute("users", users);

        return "adminPanel";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/change-role")
    public String changeUserRole(@RequestParam("userId") Long userId,
                                 @RequestParam("newRoleId") String newRoleId) {

        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            user.removeAllRoles();
            if(newRoleId.equals("USR")){
                user.addRole(roleRepo.findById("USR"));
            } else if (newRoleId.equals("MDR")){
                user.addRole(roleRepo.findById("USR"));
                user.addRole(roleRepo.findById("MDR"));
            }else if (newRoleId.equals("ADM")){
                user.addRole(roleRepo.findById("USR"));
                user.addRole(roleRepo.findById("MDR"));
                user.addRole(roleRepo.findById("ADM"));
            }
            userRepo.save(user);

            // Вернуть пользователя на страницу администратора после изменения
            return "redirect:/admin-panel";
        }
        return "redirect:/admin-panel";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("userId") Long userId) {

        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            // Удаление пользователя из репозитория
            userRepo.delete(user);

            // Вернуть пользователя на страницу администратора после удаления
            return "redirect:/admin-panel";
        }

        // Если пользователь не найден, вернуть на страницу с сообщением об ошибке или другую страницу
        return "redirect:/admin-panel?error=UserNotFound";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/block-user")
    public String blockUser(@RequestParam("userId") Long userId) {

        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            // Установка флага блокировки пользователя
            user.setAccountNonLocked(false); // Пример, как можно изменить флаг блокировки

            // Сохранение обновленной информации пользователя в репозитории
            userRepo.save(user);

            // Вернуть пользователя на страницу администратора после блокировки
            return "redirect:/admin-panel";
        }

        // Если пользователь не найден, вернуть на страницу с сообщением об ошибке или другую страницу
        return "redirect:/admin-panel?error=UserNotFound";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/unblock-user")
    public String unblockUser(@RequestParam("userId") Long userId) {

        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            // Установка флага блокировки пользователя
            user.setAccountNonLocked(true); // Пример, как можно изменить флаг блокировки

            // Сохранение обновленной информации пользователя в репозитории
            userRepo.save(user);

            // Вернуть пользователя на страницу администратора после блокировки
            return "redirect:/admin-panel";
        }

        // Если пользователь не найден, вернуть на страницу с сообщением об ошибке или другую страницу
        return "redirect:/admin-panel?error=UserNotFound";
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/toggle-moderator")
//    public String toggleModeratorStatus(@RequestParam("userId") Long userId, @RequestParam("makeModerator") boolean makeModerator) {
//        User user = userRepo.findById(userId).orElse(null);
//
//        if (user != null) {
//            if (makeModerator) {
//                // Если нужно сделать пользователя модератором, добавьте ему соответствующую роль
//                Role moderatorRole = roleRepo.findById("MDR"); // Получение роли модератора из репозитория ролей
//                user.addRole(moderatorRole);
//            } else {
//                // Если нужно убрать у пользователя статус модератора, удалите роль модератора
//                Role moderatorRole = roleRepo.findById("MDR"); // Получение роли модератора из репозитория ролей
//                user.removeRole(moderatorRole);
//            }
//            userRepo.save(user); // Сохранение обновленной информации о пользователе
//        }
//
//        return "redirect:/admin-panel"; // Перенаправление на страницу админ-панели после выполнения операции
//    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/toggle-admin")
//    public String toggleAdministratorStatus(@RequestParam("userId") Long userId, @RequestParam("makeAdmin") boolean makeAdmin) {
//        User user = userRepo.findById(userId).orElse(null);
//        Role adminRole = roleRepo.findById("ADM");
//        if (user != null) {
//            if (makeAdmin) {
//                user.addRole(adminRole);
//            } else {
//                user.removeRole(adminRole);
//            }
//            userRepo.save(user); // Сохранение обновленной информации о пользователе
//        }
//
//        return "redirect:/admin-panel"; // Перенаправление на страницу админ-панели после выполнения операции
//    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/{id}")
//    public String updateUser(@PathVariable Long id,
//                            @ModelAttribute("regForm") @Valid RegistrationForm form,
//                            BindingResult result,
//                            @RequestParam("photo") MultipartFile photo,
//                            Model model, Errors errors,
//                            SessionStatus sessionStatus,
//                            @AuthenticationPrincipal User user) {
//
//        if (errors.hasErrors()) {
//            model.addAttribute("errors", result.getAllErrors());
//            model.addAttribute("regForm", form);
//            User userToEdit = userRepo.findUserById(id);
//            model.addAttribute("user", userToEdit);
//            return "editUser";
//        }
//
//        User userToUpdate = form.toUser(passwordEncoder,roleRepo);
//        try {
//            if(photo.getSize() > 0) {
//                form.setPhoto(photo);
//            }else{
//                User userFromDB = userRepo.findUserById(id);
//                userToUpdate.setPhoto(userFromDB.getPhoto());
//            }
//        } catch (Exception e) {
//            return "redirect:/admin-panel/{id}/edit?error";
//        }
//        userToUpdate.setId(id);
//
//        userRepo.save(userToUpdate);
//
//        sessionStatus.setComplete();
//
//        return "redirect:/lots";
//    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/{id}/edit")
//    public String editUser(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
//        User userToUpdate = userRepo.findUserById(id);
//        model.addAttribute("user", userToUpdate);
//        model.addAttribute("userForm", getUserFormWithUser(userToUpdate));
//        return "editLot";
//    }
}