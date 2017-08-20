package com.development.backend.ng.springmvc.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.development.backend.ng.springmvc.domain.Comment;
import com.development.backend.ng.springmvc.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPost(Post post);

    public Page<Comment> findByPostId(Long id, Pageable page);

}
