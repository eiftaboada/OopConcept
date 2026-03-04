interface World {
    public void start();
}

class Easy implements World {
    @Override
    public void start() {
        System.out.println("Welcome to Easy Level");
    }
}

class Medium implements World {
    @Override
    public void start() {
        System.out.println("Welcome to Medium Level");
    }
}

class Hard implements World {
    @Override
    public void start() {
        System.out.println("Welcome to Hard Level");
    }
}
