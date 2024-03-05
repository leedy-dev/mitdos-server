package com.dyprj.mitd.domain.test;

import com.dyprj.mitd.domain.test.entity.Testtb;
import com.dyprj.mitd.domain.test.repository.TesttbRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TesttbTest {

    @Autowired
    private TesttbRepository testtbRepository;

    @Test
    @DisplayName("TB_TESTTB 조회")
    void findTesttbById() {
        Optional<Testtb> testtbOp = testtbRepository.findById(1L);

        Testtb testtb = testtbOp.orElseGet(() -> Testtb.builder().build());

        assertThat(testtb.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("TB_TESTTB 등록")
    void saveTesttb() {
        Testtb tb = Testtb.builder()
                .id(1L)
                .name("name1")
                .build();

        Testtb saveTb = testtbRepository.save(tb);

        assertThat(saveTb.getId()).isEqualTo(1L);
    }

}
