package br.com.palm.devshowcase.model;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String authorName;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Feedback() {}

    public Long getId() { return id; }
    public String getAuthorName() { return authorName; }
    public String getComment() { return comment; }
    public Integer getRating() { return rating; }
    public Project getProject() { return project; }

    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public void setComment(String comment) { this.comment = comment; }
    public void setRating(Integer rating) { this.rating = rating; }
    public void setProject(Project project) { this.project = project; }
}