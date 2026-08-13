package gg.vape.module;

public enum MinecraftVersionComparisonOperator {
    EQUALS{

        @Override
        public boolean N(int left, int right) {
            return left == right;
        }
    }
    ,
    NOT_EQUAL{

        @Override
        public boolean N(int left, int right) {
            return left != right;
        }
    }
    ,
    GREATER_THAN{

        @Override
        public boolean N(int left, int right) {
            return left > right;
        }
    }
    ,
    GREATHER_THAN_OR_EQUAL{

        @Override
        public boolean N(int left, int right) {
            return left >= right;
        }
    }
    ,
    LESS_THAN{

        @Override
        public boolean N(int left, int right) {
            return left < right;
        }
    }
    ,
    LESS_THAN_OR_EQUAL{

        @Override
        public boolean N(int left, int right) {
            return left <= right;
        }
    };


    private MinecraftVersionComparisonOperator() {
    }

    public abstract boolean N(int var1, int var2);

}
