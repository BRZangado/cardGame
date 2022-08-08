package com.letscode.cardgame.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "round")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Player player;
    @ManyToMany
    private List<Card> previousCards = new ArrayList<>();
    @OneToOne
    private Card currentCard;
    private int playerScore = 0;
    private boolean finished = false;
    private int retries = 0;

    public void addRetry(){
        this.retries++;
    }

    public void addScore(){
        this.playerScore++;
    }
}
