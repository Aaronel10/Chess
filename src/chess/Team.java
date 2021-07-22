package chess;

public enum Team{
   WHITE{
       @Override
       public int getDirection()
       {
           return -1;
       }
   },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }
    };

    public abstract int getDirection();

}
