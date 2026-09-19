package br.com.palm.devshowcase.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "technology")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany(mappedBy = "technologies")
    private Set<Project> projects = new HashSet<>();

    public Technology() {}
    public Technology(String name) { this.name = name; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<Project> getProjects() { return projects; }
    public void setName(String name) { this.name = name; }
}