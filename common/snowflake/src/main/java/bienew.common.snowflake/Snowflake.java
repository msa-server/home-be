package bienew.common.snowflake;

import java.util.random.RandomGenerator;

public class Snowflake {
    /**
     * 키 구조 64bit
     * (01 bits) unused
     * (41 bits) 밀리 초 타임 스탬프
     * (10 bits) 서버 번호
     * (12 bits) 시퀀스 넘버, 동 시간대에 생성한 키의 순서.
     */

    private static final int NODE_ID_BITS = 10;

    // 같은 밀리초에서 동시에 생성할 수 있는 데이터 개수 : 2 ^ 12
    private static final int SEQUENCE_NUM_BITS = 12;

    private static final long maxNodeId = (1L << NODE_ID_BITS) - 1;
    private static final long maxSequence = (1L << SEQUENCE_NUM_BITS) - 1;

    // 랜덤으로 노드 아이디 생성.
    private final long nodeId = RandomGenerator.getDefault().nextLong(maxNodeId + 1);

    // UTC = 2025-01-01T00:00:00Z
    private final long startTimeMillis = 1735689600000L;

    private long lastTimeMillis = startTimeMillis;
    private long sequence = 0L;

    public synchronized long nextId() {
        long currentTimeMillis = System.currentTimeMillis();

        if (currentTimeMillis < lastTimeMillis) {
            throw new IllegalStateException("Invalid Time");
        }

        if (currentTimeMillis == lastTimeMillis) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                currentTimeMillis = waitNextMillis(currentTimeMillis);
            }
        } else {
            sequence = 0;
        }

        lastTimeMillis = currentTimeMillis;

        return ((currentTimeMillis - startTimeMillis) << (NODE_ID_BITS + SEQUENCE_NUM_BITS))
                | (nodeId << SEQUENCE_NUM_BITS)
                | sequence;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp <= lastTimeMillis) {
            currentTimestamp = System.currentTimeMillis();
        }
        return currentTimestamp;
    }

}
