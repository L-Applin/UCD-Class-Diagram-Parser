package syntxTree;

import syntxTree.expressions.Expression;
import token.UmlToken;

import java.util.*;

/**
 *
 */
public class UmlContext implements Map<String, Expression> {

    private Map<String, Expression> tokens;

    public UmlContext(Map<String, Expression> map){
        this.tokens = map;
    }

    // default use TreeMap
    public UmlContext(){
        this.tokens = new TreeMap<>();
    }




    // MAP IMPLEMENTATION :)
    @Override
    public int size() {
        return tokens.size();
    }

    @Override
    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return tokens.containsKey((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return tokens.containsValue((Expression) value);
    }

    @Override
    public Expression get(Object key) {
        return tokens.get((String) key);
    }

    @Override
    public Expression put(String key, Expression value) {
        return tokens.put(key, value);
    }

    @Override
    public Expression remove(Object key) {
        return tokens.remove((String) key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Expression> m) {
        tokens.putAll(m);
    }

    @Override
    public void clear() {
        tokens.clear();
    }

    @Override
    public Set<String> keySet() {
        return tokens.keySet();
    }

    @Override
    public Collection<Expression> values() {
        return tokens.values();
    }

    @Override
    public Set<Entry<String, Expression>> entrySet() {
        return tokens.entrySet();
    }

}
