package reconstruct.newcode.one;

import reconstruct.newcode.IdGenerator;

public class RandomIdGeneratorTest  {

    public static void main(String[] args) {
        IdGenerator idGenerator = new RandomIdGenerator();
        System.out.println(idGenerator.generate());
    }
}