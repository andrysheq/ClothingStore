package com.web.auction.controllers;

import com.web.auction.data.RoleRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.Lot;
import com.web.auction.models.Role;
import com.web.auction.models.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Component
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin-panel")
public class AdminPanelController {
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    public AdminPanelController (UserRepository userRepo, RoleRepository roleRepo){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
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
            // Изменение роли пользователя
            // Например, user.setRole(newRole); или user.getRoles().clear(); user.addRole(newRole);
            // Сохранение пользователя в репозитории userRepo.save(user);

            user.addRole(roleRepo.findById(newRoleId));

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/toggle-moderator")
    public String toggleModeratorStatus(@RequestParam("userId") Long userId, @RequestParam("makeModerator") boolean makeModerator) {
        User user = userRepo.findById(userId).orElse(null);

        if (user != null) {
            if (makeModerator) {
                // Если нужно сделать пользователя модератором, добавьте ему соответствующую роль
                Role moderatorRole = roleRepo.findById("MDR"); // Получение роли модератора из репозитория ролей
                user.addRole(moderatorRole);
            } else {
                // Если нужно убрать у пользователя статус модератора, удалите роль модератора
                Role moderatorRole = roleRepo.findById("MDR"); // Получение роли модератора из репозитория ролей
                user.removeRole(moderatorRole);
            }
            userRepo.save(user); // Сохранение обновленной информации о пользователе
        }

        return "redirect:/admin-panel"; // Перенаправление на страницу админ-панели после выполнения операции
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/toggle-admin")
    public String toggleAdministratorStatus(@RequestParam("userId") Long userId, @RequestParam("makeAdmin") boolean makeAdmin) {
        User user = userRepo.findById(userId).orElse(null);
        Role adminRole = roleRepo.findById("ADM");
        if (user != null) {
            if (makeAdmin) {
                user.addRole(adminRole);
            } else {
                user.removeRole(adminRole);
            }
            userRepo.save(user); // Сохранение обновленной информации о пользователе
        }

        return "redirect:/admin-panel"; // Перенаправление на страницу админ-панели после выполнения операции
    }
}