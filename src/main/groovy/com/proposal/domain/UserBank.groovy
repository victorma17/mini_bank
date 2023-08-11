package com.proposal.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

import static jakarta.persistence.GenerationType.AUTO

@CompileStatic
@Serdeable
@Entity

@Table(name = 'UserBank')
class UserBank {

    @Id
    @GeneratedValue(strategy = AUTO)
    Long user_id

    @NotNull
    @Column(name = 'user_name', nullable = false, unique = true)
    String user_name

    @JsonIgnore
  
    UserBank() {}

    UserBank(@NotNull String user_name) {
        this.user_name = user_name
    }

    @Override
    String toString() {
        "UserBank{user_id=$user_id, user_name='$user_name'}"
    }
}
