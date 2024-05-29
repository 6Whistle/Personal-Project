package com.whistle6.api.toeic.model;

import com.whistle6.api.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString(exclude = {"id"})
public class Toeic extends BaseEntity{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "part", nullable = false)
    private Integer part;

    @Setter
    @Column(name = "question", nullable = false, unique = true)
    private String question;

    @Setter
    @Column(name = "answer", nullable = false)
    private String answer;

    @Setter
    @Column(name = "image")
    private String image;

    @Setter
    @Column(name = "choices")
    private String choices;
}
