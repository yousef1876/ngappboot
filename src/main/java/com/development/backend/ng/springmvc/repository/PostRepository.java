package com.development.backend.ng.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.development.backend.ng.springmvc.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>,//
        JpaSpecificationExecutor<Post>{

}
