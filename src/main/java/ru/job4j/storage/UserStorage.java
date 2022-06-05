package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
       return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) throws Exception {
        User first = users.get(fromId);
        User second = users.get(toId);
        if (first != null && second != null) {
            if (first.getAmount() < amount) {
                throw new Exception("На счёте недостаточно средств");
            }
            first.setAmount(first.getAmount() - amount);
            second.setAmount(second.getAmount() + amount);
            return true;
        }
        return false;
    }
}
