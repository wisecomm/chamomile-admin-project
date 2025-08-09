package net.lotte.chamomile.admin.transaction;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    @Override
    public void test() {
        testRepository.save(new Test(1L,"test",20));
        testRepository.save(new Test(2L,"test",20));
    }
}
