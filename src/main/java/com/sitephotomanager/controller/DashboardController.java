package com.sitephotomanager.controller;

import com.sitephotomanager.repository.FloorPlanRepository;
import com.sitephotomanager.repository.PhotoRepository;
import com.sitephotomanager.repository.ProjectRepository;
import com.sitephotomanager.repository.RoomRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ProjectRepository projectRepository;
    private final FloorPlanRepository floorPlanRepository;
    private final RoomRepository roomRepository;
    private final PhotoRepository photoRepository;

    public DashboardController(ProjectRepository projectRepository,
                               FloorPlanRepository floorPlanRepository,
                               RoomRepository roomRepository,
                               PhotoRepository photoRepository) {
        this.projectRepository = projectRepository;
        this.floorPlanRepository = floorPlanRepository;
        this.roomRepository = roomRepository;
        this.photoRepository = photoRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("totalProjects", projectRepository.count());
        model.addAttribute("totalFloorPlans", floorPlanRepository.count());
        model.addAttribute("totalRooms", roomRepository.count());
        model.addAttribute("totalPhotos", photoRepository.count());
        model.addAttribute("recentPhotos", photoRepository.findTop10ByOrderByUploadedAtDesc());
        model.addAttribute("userEmail", authentication != null ? authentication.getName() : "");

        return "dashboard";
    }
}
