package com.anugrah.projects.xmeme.crio.repository;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {

	List<Meme> findTop100ByOrderByIdDesc();

	boolean existsMemeByCaption(String caption);

	boolean existsMemeByName(String name);

	boolean existsMemeByUrl(String url);

}
