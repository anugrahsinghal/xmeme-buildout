package com.anugrah.projects.xmeme.crio.repository;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {
}
