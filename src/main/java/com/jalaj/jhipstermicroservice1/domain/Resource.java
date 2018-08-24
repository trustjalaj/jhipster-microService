package com.jalaj.jhipstermicroservice1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jalaj.jhipstermicroservice1.domain.enumeration.ResourceType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_description")
    private String resourceDescription;

    @Column(name = "resource_url")
    private String resourceURL;

    @Column(name = "resource_preview_image")
    private String resourcePreviewImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    @JsonIgnoreProperties("resources")
    private Discipline discipline;

    @ManyToOne
    @JsonIgnoreProperties("resources")
    private Program program;

    @ManyToOne
    @JsonIgnoreProperties("resources")
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties("resources")
    private Lesson lesson;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Resource resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public Resource resourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
        return this;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    public Resource resourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
        return this;
    }

    public String getResourcePreviewImage() {
        return resourcePreviewImage;
    }

    public void setResourcePreviewImage(String resourcePreviewImage) {
        this.resourcePreviewImage = resourcePreviewImage;
    }

    public Resource resourcePreviewImage(String resourcePreviewImage) {
        this.resourcePreviewImage = resourcePreviewImage;
        return this;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Resource resourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Resource weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Resource discipline(Discipline discipline) {
        this.discipline = discipline;
        return this;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Resource program(Program program) {
        this.program = program;
        return this;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Resource course(Course course) {
        this.course = course;
        return this;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Resource lesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource resource = (Resource) o;
        if (resource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", resourceName='" + getResourceName() + "'" +
            ", resourceDescription='" + getResourceDescription() + "'" +
            ", resourceURL='" + getResourceURL() + "'" +
            ", resourcePreviewImage='" + getResourcePreviewImage() + "'" +
            ", resourceType='" + getResourceType() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
