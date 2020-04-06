package com.pantrychef.pantrychef.repositories;

import com.pantrychef.pantrychef.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepo extends JpaRepository<Comments, Long> {
}
