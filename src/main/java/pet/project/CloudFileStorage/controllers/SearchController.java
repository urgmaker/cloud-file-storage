package pet.project.CloudFileStorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pet.project.CloudFileStorage.dto.MinioObjectDto;
import pet.project.CloudFileStorage.models.User;
import pet.project.CloudFileStorage.services.SearchService;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public String search(@AuthenticationPrincipal User user, @RequestParam("query") String query, Model model) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }

        List<MinioObjectDto> results = searchService.search(user.getUsername(), query);

        model.addAttribute("searchResults", results.isEmpty() ? null : results);

        return "search";
    }
}
