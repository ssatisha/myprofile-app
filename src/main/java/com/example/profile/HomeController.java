package com.example.profile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    // Sample blog post data
    private final List<BlogPost> posts = List.of(
        new BlogPost("deploying-spring-boot-azure", 
            "Deploying Spring Boot to Azure Container Instances", 
            "Learn how to containerize a Spring Boot application and deploy it using Docker and Azure.",
            "Deploying Spring Boot apps on Azure is simple with Azure Container Instances (ACI). First, package your app into a JAR file, build a Docker image, push it to Azure Container Registry (ACR), and deploy...",
            "2026-03-15"),
        new BlogPost("learning-java-21", 
            "My Favorite Features in Java 21", 
            "A look at Virtual Threads, Pattern Matching, and Record Patterns.",
            "Java 21 brought game-changing features. Virtual Threads significantly simplify high-throughput concurrent applications without blocking platform threads...",
            "2026-02-10")
    );

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Sati");
        model.addAttribute("title", "Java & Cloud Developer");
        model.addAttribute("hobbies", List.of("✈️ Global Travel", "📸 Photography", "☕ Specialty Coffee", "📚 Tech Reading"));
        
        // Pass blog posts to home page
        model.addAttribute("posts", posts);
        
        return "index";
    }

    // Endpoint for individual blog posts
    @GetMapping("/blog/{id}")
    public String blogPost(@PathVariable String id, Model model) {
        BlogPost post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (post == null) {
            return "redirect:/";
        }

        model.addAttribute("post", post);
        return "post"; // Renders post.html
    }
}