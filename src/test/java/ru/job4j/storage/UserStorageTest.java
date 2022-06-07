package ru.job4j.storage;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    private class ThreadUserStorage extends Thread {

        private final UserStorage storage;
        private final int id1;
        private final int id2;
        private final int amount;

        private ThreadUserStorage(final UserStorage storage, int id1, int id2, int amount) {
            this.storage = storage;
            this.id1 = id1;
            this.id2 = id2;
            this.amount = amount;
        }

        @Override
        public void run() {
            try {
                this.storage.transfer(id1, id2, amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void whenAddAndUpdate() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(1, 250);
        User user3 = new User(3, 300);
        storage.add(user1);
        assertTrue(storage.update(user2));
        assertFalse(storage.update(user3));
    }

    @Test
    public void whenAddAndDelete() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(1, 250);
        storage.add(user1);
        assertTrue(storage.delete(user1));
        assertFalse(storage.update(user2));
    }

    @Test(expected = Exception.class)
    public void whenAmountIsNotEnough() throws Exception {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 250);
        storage.add(user1);
        storage.add(user2);
        storage.transfer(1, 2, 150);
    }

    @Test
    public void whenTransferAndUserIsNull() throws Exception {
        UserStorage storage = new UserStorage();
        assertFalse(storage.transfer(1, 2, 150));
    }

    @Test
    public void whenMultiThreadedTransfer() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 350);
        User user2 = new User(2, 250);
        storage.add(user1);
        storage.add(user2);
        Thread first = new ThreadUserStorage(storage, 1, 2, 150);
        Thread second = new ThreadUserStorage(storage, 1, 2, 75);
        Thread third = new ThreadUserStorage(storage, 2, 1, 170);
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(user1.getAmount(), is(295));
        assertThat(user2.getAmount(), is(305));
    }
}