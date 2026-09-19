package br.com.palm.devshowcase.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 500)
    private String projectUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToMany
    @JoinTable(
        name = "project_technology",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private Set<Technology> technologies = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();

    public Project() {}

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getProjectUrl() { return projectUrl; }
    public Profile getProfile() { return profile; }
    public Set<Technology> getTechnologies() { return technologies; }
    public List<Feedback> getFeedbacks() { return feedbacks; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setProjectUrl(String projectUrl) { this.projectUrl = projectUrl; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public void setTechnologies(Set<Technology> technologies) { this.technologies = technologies; }
}