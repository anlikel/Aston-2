package org.example;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap;

/**
 * Пользовательская реализация интерфейса Map на основе хэш-таблицы.
 * Использует метод цепочек для разрешения коллизий.
 *
 * <p>Данная реализация предоставляет все опциональные операции Map и поддерживает
 * null в качестве ключей и значений.</p>
 *
 * <p>Карта использует динамический массив узлов, где каждый узел представляет
 * пару ключ-значение. При возникновении коллизий узлы хранятся в виде связного списка
 * в одном и том же индексе корзины.</p>
 *
 * <p>Реализация автоматически изменяет размер внутреннего массива при превышении
 * коэффициента загрузки или когда карта становится слишком разреженной, поддерживая
 * эффективную производительность для операций put и get.</p>
 *
 * @param <K> тип ключей, поддерживаемых этой картой
 * @param <V> тип отображаемых значений
 */
public class MyHashMapImp<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;

    private int capacity;
    private int size;
    private double loadFactor = 0.8;

    private Node[] table;

    /**
     * Создает новую пустую карту с начальной емкостью по умолчанию (16).
     */
    public MyHashMapImp() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
        capacity = DEFAULT_CAPACITY;
    }

    /**
     * Возвращает количество ключ-значение пар в этой карте.
     *
     * @return количество пар в карте
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли карта.
     *
     * @return true если карта не содержит пар, false в противном случае
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Проверяет, содержится ли указанный ключ в карте.
     *
     * @param key ключ для проверки
     * @return true если карта содержит ключ, false в противном случае
     */
    @Override
    public boolean containsKey(Object key) {
        if (size == 0) {
            return false;
        }
        return checkKey(key);
    }

    /**
     * Проверяет, содержится ли указанное значение в карте.
     *
     * @param value значение для проверки
     * @return true если карта содержит значение, false в противном случае
     */
    @Override
    public boolean containsValue(Object value) {
        boolean result = false;
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                result = checkValue(table[i], value);
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    /**
     * Возвращает значение, связанное с указанным ключом.
     *
     * @param key ключ, значение которого нужно получить
     * @return значение, связанное с ключом, или null если ключ не найден
     */
    @Override
    public V get(Object key) {
        if (size == 0) {
            return null;
        }
        int hash = getHashCode(key);
        int index = getIndex(hash);
        V resVal = null;
        if (table[index] != null) {
            resVal = getNode(index, hash, key);
        }
        return resVal;
    }

    /**
     * Связывает указанное значение с указанным ключом в этой карте.
     *
     * @param key   ключ для связи со значением
     * @param value значение для связи с ключом
     * @return предыдущее значение, связанное с ключом, или null если ключ не существовал
     */
    @Override
    public V put(K key, V value) {
        int hash = getHashCode(key);
        int index = getIndex(hash);
        V resVal = null;

        if (table[index] == null) {
            Node<K, V> newNode = new Node(hash, key, value, null);
            table[index] = newNode;
            size++;
        } else {
            resVal = addNode(index, hash, key, value);
        }
        resize();
        return resVal;
    }

    /**
     * Удаляет пару ключ-значение для указанного ключа.
     *
     * @param key ключ, который нужно удалить
     * @return значение, связанное с удаленным ключом, или null если ключ не найден
     */
    @Override
    public V remove(Object key) {
        if (size == 0) {
            return null;
        }
        int hash = getHashCode(key);
        int index = getIndex(hash);
        V resVal = null;
        if (table[index] != null) {
            resVal = removeNode(index, hash, key);
        }
        resize();
        return resVal;
    }

    /**
     * Копирует все пары ключ-значение из указанной карты в эту карту.
     *
     * @param m карта, пары которой будут скопированы
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Set<? extends K> keys = m.keySet();
        for (K key : keys) {
            V value = m.get(key);
            this.put(key, value);
        }
    }

    /**
     * Удаляет все пары ключ-значение из карты.
     */
    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    /**
     * Возвращает множество всех ключей, содержащихся в этой карте.
     *
     * @return множество ключей карты
     */
    @Override
    public Set<K> keySet() {
        if (size == 0) {
            return Collections.emptySet();
        }
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                addKeys(table[i], keys);
            }
        }
        return keys;
    }

    /**
     * Возвращает коллекцию всех значений, содержащихся в этой карте.
     *
     * @return коллекция значений карты
     */
    @Override
    public Collection<V> values() {
        if (size == 0) {
            return Collections.emptySet();
        }
        Set<V> values = new HashSet<>();
        Set<K> keys = keySet();
        for (K key : keys) {
            values.add(get(key));
        }
        return values;
    }

    /**
     * Возвращает множество всех пар ключ-значение, содержащихся в этой карте.
     *
     * @return множество пар ключ-значение карты
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        if (size == 0) {
            return Collections.emptySet();
        }
        Set<Entry<K, V>> entries = new HashSet<>();
        Set<K> keys = keySet();
        for (K key : keys) {
            V value = get(key);
            Entry<K, V> entry = new AbstractMap.SimpleEntry(key, value);
            entries.add(entry);
        }
        return entries;
    }

    /**
     * Вычисляет хэш-код для ключа.
     *
     * @param key ключ для вычисления хэш-кода
     * @return хэш-код ключа
     */
    private <K> int getHashCode(K key) {
        return Objects.hashCode(key);
    }

    /**
     * Вычисляет индекс в массиве table на основе хэш-кода.
     *
     * @param hash хэш-код ключа
     * @return индекс в массиве table
     */
    private int getIndex(int hash) {
        return hash & (capacity - 1);
    }

    /**
     * Добавляет новый узел или обновляет существующий в случае коллизии.
     *
     * @param index индекс в массиве table
     * @param hash  хэш-код ключа
     * @param key   ключ
     * @param value значение
     * @return предыдущее значение, связанное с ключом, или null если ключ не существовал
     */
    private V addNode(int index, int hash, K key, V value) {
        V resVal = null;
        Node<K, V> currentNode = table[index];
        Node<K, V> lastNode = null;

        while (currentNode != null) {
            if (currentNode.hash == hash && (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {
                resVal = currentNode.value;
                currentNode.value = value;
                break;
            }
            lastNode = currentNode;
            currentNode = currentNode.next;
        }
        if (resVal == null && lastNode != null && lastNode.next == null) {
            lastNode.next = new Node<>(hash, key, value, null);
            size++;
        }
        return resVal;
    }

    /**
     * Находит и возвращает значение узла по ключу.
     *
     * @param index индекс в массиве table
     * @param hash  хэш-код ключа
     * @param key   ключ для поиска
     * @return значение, связанное с ключом, или null если ключ не найден
     */
    private V getNode(int index, int hash, Object key) {
        V resVal = null;
        Node<K, V> currentNode = table[index];
        while (currentNode != null) {
            if (currentNode.hash == hash && (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {
                resVal = currentNode.value;
                break;
            }
            currentNode = currentNode.next;
        }
        return resVal;
    }

    /**
     * Удаляет узел по ключу.
     *
     * @param index индекс в массиве table
     * @param hash  хэш-код ключа
     * @param key   ключ для удаления
     * @return значение удаленного узла или null если ключ не найден
     */
    private V removeNode(int index, int hash, Object key) {
        V resVal = null;
        Node<K, V> currentNode = table[index];
        Node<K, V> lastNode = null;

        while (currentNode != null) {
            if (currentNode.hash == hash && (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {
                resVal = currentNode.value;
                size--;
                break;
            }
            lastNode = currentNode;
            currentNode = currentNode.next;
        }
        if (resVal != null && lastNode != null && currentNode != null) {
            lastNode.next = currentNode.next;
        } else if (resVal != null && lastNode == null) {
            table[index] = currentNode.next;
        }
        return resVal;
    }

    /**
     * Проверяет наличие ключа в карте.
     *
     * @param key ключ для проверки
     * @return true если ключ существует, false в противном случае
     */
    private boolean checkKey(Object key) {
        int hash = getHashCode(key);
        int index = getIndex(hash);
        boolean result = false;
        if (table[index] != null) {
            Node<K, V> currentNode = table[index];
            while (currentNode != null) {
                if (currentNode.hash == hash && (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {
                    result = true;
                    break;
                }
                currentNode = currentNode.next;
            }
        }
        return result;
    }

    /**
     * Проверяет наличие значения в карте.
     *
     * @param node  начальный узел для поиска
     * @param value значение для проверки
     * @return true если значение существует, false в противном случае
     */
    private boolean checkValue(Node<K, V> node, Object value) {
        boolean result = false;
        while (node != null) {
            if (value == null ? node.value == null : value.equals(node.value)) {
                result = true;
                break;
            }
            node = node.next;
        }
        return result;
    }

    /**
     * Добавляет все ключи из цепочки узлов в множество.
     *
     * @param node начальный узел цепочки
     * @param set  множество для добавления ключей
     */
    void addKeys(Node<K, V> node, Set<K> set) {
        while (node != null) {
            set.add(node.key);
            node = node.next;
        }
    }

    /**
     * Изменяет размер внутренней таблицы при необходимости.
     * Увеличивает размер при превышении коэффициента загрузки и
     * уменьшает при сильном разрежении таблицы.
     */
    private void resize() {
        if (size >= capacity * loadFactor) {
            int oldCapacity = capacity;
            int newCapacity = (int) (capacity * 1.5);
            Node[] oldTable = table;
            table = new Node[newCapacity];
            capacity = newCapacity;
            addAllNodes(oldTable, oldCapacity);
        } else if (size < ((capacity / 1.5) * loadFactor - 1) && capacity > 16) {
            int oldCapacity = capacity;
            int newCapacity = (int) (capacity / 1.5);
            Node[] oldTable = table;
            table = new Node[newCapacity];
            capacity = newCapacity;
            addAllNodes(oldTable, oldCapacity);
        }
    }

    /**
     * Перемещает все узлы из старой таблицы в новую при изменении размера.
     *
     * @param oldTable    старая таблица узлов
     * @param oldCapacity емкость старой таблицы
     */
    private void addAllNodes(Node[] oldTable, int oldCapacity) {
        for (int i = 0; i < oldCapacity; i++) {
            if (oldTable[i] != null) {
                Node<K, V> currentNode = oldTable[i];
                while (currentNode != null) {
                    this.putResize(currentNode.key, currentNode.value);
                    currentNode = currentNode.next;
                }
            }
        }
    }

    /**
     * Специальная версия метода put для использования при изменении размера.
     * Не вызывает рекурсивное изменение размера.
     *
     * @param key   ключ для добавления
     * @param value значение для добавления
     */
    public void putResize(K key, V value) {
        int hash = getHashCode(key);
        int index = getIndex(hash);

        if (table[index] == null) {
            Node<K, V> newNode = new Node(hash, key, value, null);
            table[index] = newNode;
        } else {
            addNodeResize(index, hash, key, value);
        }
    }

    /**
     * Специальная версия метода addNode для использования при изменении размера.
     *
     * @param index индекс в массиве table
     * @param hash  хэш-код ключа
     * @param key   ключ
     * @param value значение
     */
    private void addNodeResize(int index, int hash, K key, V value) {
        Node<K, V> currentNode = table[index];
        Node<K, V> lastNode = null;
        int flag = 0;
        while (currentNode != null) {
            if (currentNode.hash == hash && (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {
                currentNode.value = value;
                flag = 1;
                break;
            }
            lastNode = currentNode;
            currentNode = currentNode.next;
        }
        if (flag == 0 && lastNode != null && lastNode.next == null) {
            lastNode.next = new Node<>(hash, key, value, null);
        }
    }

    /**
     * Возвращает текущий размер внутренней таблицы.
     * Используется для тестирования метода resize.
     *
     * @return текущий размер внутренней таблицы
     */
    public int getTableSize() {
        return table.length;
    }

    /**
     * Внутренний класс для представления узла в хэш-таблице.
     * Содержит пару ключ-значение и ссылку на следующий узел в цепочке.
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node next;

        /**
         * Создает новый узел с указанными параметрами.
         *
         * @param hash  хэш-код ключа
         * @param key   ключ узла
         * @param value значение узла
         * @param next  следующий узел в цепочке
         */
        Node(int hash, K key, V value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}