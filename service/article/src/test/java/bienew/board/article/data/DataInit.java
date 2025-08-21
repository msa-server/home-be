package bienew.board.article.data;

import bienew.board.article.entity.Article;
import bienew.board.article.entity.Tag;
import bienew.common.snowflake.Snowflake;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
public class DataInit {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    TransactionTemplate transactionTemplate;
    Snowflake snowflake = new Snowflake();


    static final int BULK_INSTANCE_SIZE = 2000;
    static final int EXECUTE_COUNT = 6000;
    static final int CHUNK_SIZE = 200;
    static final int CONCURRENCY = 8;

    @Test
    void initialize() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(EXECUTE_COUNT);
        Semaphore permits = new Semaphore(CONCURRENCY);

        Tag commonTag = transactionTemplate.execute(status -> {
                    Tag tag = Tag.create(
                            snowflake.nextId(),
                            "common"
                    );

                    entityManager.persist(tag);
                    entityManager.flush();

                    return tag;
                }
        );

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    permits.acquire();
                    insert(commonTag.getTagId());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    permits.release();
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        AtomicReference<Tag> tempTag = new AtomicReference<>();
    }

    void insert(Long tagId) {
        for (int start = 0; start < BULK_INSTANCE_SIZE; start += CHUNK_SIZE) {
            final int end = Math.min(start + CHUNK_SIZE, BULK_INSTANCE_SIZE);

            int finalStart = start;
            transactionTemplate.executeWithoutResult(status -> {
                Tag commonRef = entityManager.getReference(Tag.class, tagId);

                for (int i = finalStart; i < end; i++) {
                    Article article = Article.create(
                            snowflake.nextId(),
                            "title" + i,
                            "content" + i
                    );

                    article.addTag(commonRef);

                    entityManager.persist(article);
                }

                entityManager.flush();
                entityManager.clear();
            });
        }
    }
}
