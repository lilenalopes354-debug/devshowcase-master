package br.com.palm.devshowcase.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Column(length = 500)
    private String bio;

    @Column(length = 500)
    private String githubUrl;

    @Column(length = 500)
    private String linkedinUrl;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public Profile() {}

    public Profile(String name, String email, String bio, String githubUrl, String linkedinUrl) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public String getGithubUrl() { return githubUrl; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public List<Project> getProjects() { return projects; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setBio(String bio) { this.bio = bio; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
}