package com.example.profile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

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
        model.addAttribute("name", "Satisha Suryanarayana");
        model.addAttribute("title", "AzureDevOps | SRE | Automation | Observability");
        model.addAttribute("hobbies", List.of("✈️ Global Travel", "📸 Photography", "☕ Specialty Coffee", "📚 Tech Reading"));
        model.addAttribute("posts", posts);
        
        // Pass scanned files (returns empty list instead of null if no files found)
        List<String> resumeFiles = getResumeFileList();
        model.addAttribute("resumes", resumeFiles != null ? resumeFiles : new ArrayList<String>());
        
        return "index";
    }

    private List<String> getResumeFileList() {
        List<String> fileNames = new ArrayList<>();

        // 1. Direct file system scan for local IDE run
        File dir = new File("src/main/resources/static/files");
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && !file.getName().startsWith(".")) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }

        // 2. Spring Classpath pattern fallback for compiled JAR/Container execution
        if (fileNames.isEmpty()) {
            try {
                PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources("classpath*:static/files/*");
                for (Resource resource : resources) {
                    if (resource.getFilename() != null && !resource.getFilename().isEmpty()) {
                        fileNames.add(resource.getFilename());
                    }
                }
            } catch (IOException ignored) {}
        }

        return fileNames;
    }

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
        return "post";
    }
}