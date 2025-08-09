package net.lotte.chamomile.admin.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    Test findByAge(int age);
}
