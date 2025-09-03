package AWeiDianDong.BloomFilter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class BloomFilter {
    private final BitSet bits;         // 位数组，用于存放哈希映射的 bit
    private final int bitSize;         // m：位数组长度
    private final int numHashFunctions;// k：哈希函数个数（由双哈希生成）

    // 构造函数（推荐使用）
    // 通过传入预计元素个数 expectedInsertions 和期望误判率 fpp 自动计算最佳 m、k
    public BloomFilter(long expectedInsertions, double fpp) {
        if (expectedInsertions <= 0) throw new IllegalArgumentException("expectedInsertions must be > 0");
        if (!(fpp > 0.0 && fpp < 1.0)) throw new IllegalArgumentException("fpp must be in (0,1)");

        long m = optimalNumOfBits(expectedInsertions, fpp);     // 计算最佳位数组大小 m
        int k = optimalNumOfHashFunctions(expectedInsertions, m); // 计算最佳哈希函数个数 k

        if (m > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("bitSize too large for BitSet");
        }
        this.bitSize = (int) m;
        this.numHashFunctions = k;
        this.bits = new BitSet(this.bitSize);
    }

    // 备选构造函数：直接手动指定 bit 数组大小和哈希函数个数
    public BloomFilter(int bitSize, int numHashFunctions) {
        if (bitSize <= 0 || numHashFunctions <= 0) throw new IllegalArgumentException();
        this.bitSize = bitSize;
        this.numHashFunctions = numHashFunctions;
        this.bits = new BitSet(bitSize);
    }

    // 添加一个元素到 BloomFilter
    // 计算多个哈希值，映射到 bitArray 里置位为 1
    public void add(String value) {
        if (value == null) return;
        long[] hk = murmur3_x64_128(value); // 一次 Murmur3 哈希，得到两个 64 位数
        long h1 = hk[0];
        long h2 = hk[1];
        // 双哈希法：h(i) = h1 + i * h2
        for (int i = 0; i < numHashFunctions; i++) {
            long combined = h1 + (long) i * h2;
            int index = positiveModulo(combined, bitSize); // 映射到 [0, m)
            bits.set(index);
        }
    }

    // 判断某个元素是否可能存在
    // 如果对应的多个 bit 都是 1，则“可能存在”；只要有一个 bit 不是 1，则“一定不存在”
    public boolean mightContain(String value) {
        if (value == null) return false;
        long[] hk = murmur3_x64_128(value);
        long h1 = hk[0];
        long h2 = hk[1];
        for (int i = 0; i < numHashFunctions; i++) {
            long combined = h1 + (long) i * h2;
            int index = positiveModulo(combined, bitSize);
            if (!bits.get(index)) return false;
        }
        return true;
    }

    // 返回 bit 数组大小（m）
    public int bitSize() { return bitSize; }

    // 返回哈希函数个数（k）
    public int numHashFunctions() { return numHashFunctions; }

    // 根据公式计算最佳 bit 数组大小 m
    // m = -n * ln(p) / (ln2^2)
    private static long optimalNumOfBits(long n, double p) {
        return (long) Math.ceil(-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    // 根据公式计算最佳哈希函数个数 k
    // k = m/n * ln2
    private static int optimalNumOfHashFunctions(long n, long m) {
        int k = (int) Math.round(((double) m / (double) n) * Math.log(2));
        return Math.max(1, k);
    }

    // 保证取模后非负
    private static int positiveModulo(long x, int mod) {
        long r = x % mod;
        if (r < 0) r += mod;
        return (int) r;
    }

    // MurmurHash3 x64 128-bit 实现
    // 输入：字符串；输出：两个 64-bit 哈希值
    // 用于双哈希法，生成多个哈希函数结果
    private static long[] murmur3_x64_128(String key) {
        byte[] data = key.getBytes(StandardCharsets.UTF_8);
        final int length = data.length;
        final int nblocks = length / 16;

        long h1 = 0;
        long h2 = 0;

        final long c1 = 0x87c37b91114253d5L;
        final long c2 = 0x4cf5ad432745937fL;

        // body 部分：16 字节为一块
        ByteBuffer bb = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < nblocks; i++) {
            long k1 = bb.getLong(i * 16);
            long k2 = bb.getLong(i * 16 + 8);

            k1 *= c1; k1 = Long.rotateLeft(k1, 31); k1 *= c2; h1 ^= k1;
            h1 = Long.rotateLeft(h1, 27); h1 += h2; h1 = h1 * 5 + 0x52dce729;

            k2 *= c2; k2 = Long.rotateLeft(k2, 33); k2 *= c1; h2 ^= k2;
            h2 = Long.rotateLeft(h2, 31); h2 += h1; h2 = h2 * 5 + 0x38495ab5;
        }

        // tail 部分：处理不足 16 字节的剩余内容
        long k1 = 0;
        long k2 = 0;
        int tailStart = nblocks * 16;
        switch (length & 15) {
            case 15: k2 ^= ((long) data[tailStart + 14] & 0xff) << 48;
            case 14: k2 ^= ((long) data[tailStart + 13] & 0xff) << 40;
            case 13: k2 ^= ((long) data[tailStart + 12] & 0xff) << 32;
            case 12: k2 ^= ((long) data[tailStart + 11] & 0xff) << 24;
            case 11: k2 ^= ((long) data[tailStart + 10] & 0xff) << 16;
            case 10: k2 ^= ((long) data[tailStart + 9] & 0xff) << 8;
            case 9:  k2 ^= ((long) data[tailStart + 8] & 0xff);
                     k2 *= c2; k2 = Long.rotateLeft(k2, 33); k2 *= c1; h2 ^= k2;
            case 8:  k1 ^= ((long) data[tailStart + 7] & 0xff) << 56;
            case 7:  k1 ^= ((long) data[tailStart + 6] & 0xff) << 48;
            case 6:  k1 ^= ((long) data[tailStart + 5] & 0xff) << 40;
            case 5:  k1 ^= ((long) data[tailStart + 4] & 0xff) << 32;
            case 4:  k1 ^= ((long) data[tailStart + 3] & 0xff) << 24;
            case 3:  k1 ^= ((long) data[tailStart + 2] & 0xff) << 16;
            case 2:  k1 ^= ((long) data[tailStart + 1] & 0xff) << 8;
            case 1:  k1 ^= ((long) data[tailStart] & 0xff);
                     k1 *= c1; k1 = Long.rotateLeft(k1, 31); k1 *= c2; h1 ^= k1;
        }

        // 最终混淆（fmix）：保证高熵分布
        h1 ^= length;
        h2 ^= length;

        h1 += h2;
        h2 += h1;

        h1 = fmix64(h1);
        h2 = fmix64(h2);

        h1 += h2;
        h2 += h1;

        return new long[]{h1, h2};
    }

    // MurmurHash 的 fmix64 混淆函数：增加随机性和扩散性
    private static long fmix64(long k) {
        k ^= k >>> 33;
        k *= 0xff51afd7ed558ccdL;
        k ^= k >>> 33;
        k *= 0xc4ceb9fe1a85ec53L;
        k ^= k >>> 33;
        return k;
    }

    // ------------ Demo 演示用例 ------------
    public static void main(String[] args) {
        // 构造：预计 10 万个元素，目标误判率 1%
        BloomFilter bf = new BloomFilter(100_000, 0.01);

        bf.add("hello");
        bf.add("world");

        System.out.println("hello -> " + bf.mightContain("hello")); // true
        System.out.println("world -> " + bf.mightContain("world")); // true
        System.out.println("bloom -> " + bf.mightContain("bloom")); // 有一定概率 true（误判）
        System.out.printf("m=%d, k=%d%n", bf.bitSize(), bf.numHashFunctions());
    }
}
