import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Operator<T extends  Collection<I>,I> {
    /*
    @ modify - статический, принимает исходную коллекцию и оборачивает ее в Operator
    @ add - добавляет переданный элемент в коллекцию
    @ add - добавляет все элементы переданной коллекции в коллекцию
    @ remove - удаляет элементы коллекции согласно переданному предикату
    @ sort - сортирует элементы коллекции
    each - передает все элементы коллекции потребителю
    copyTo - копирует все элементы коллекции в новую
    convertTo - преобразует каждый элемент коллекции и копирует результаты в новую
    @ get - возвращает результат
     */
    private T collection;

    private Operator(T c){
        collection = c;
    }

    public static <T extends  Collection<I>, I> Operator<T, I> modify(T c){
        return new Operator(c);
    }

    public Operator<T, I> add(Collection<I> c){
        collection.addAll(c);
        return this;
    }

    public Operator<T, I> add(I t){
        this.collection.add(t);
        return this;
    }

    public Operator<T, I> remove(Predicate<? super I> predicate){
        collection.removeIf(predicate);
        return this;
    }

    public Operator<T, I> sort(Comparator<? super I> comp){
        ArrayList al = new ArrayList(collection);
        Collections.sort(al, comp);
        collection = (T) al;
        return this;
    }

    public Operator<T, I> each(Consumer<? super I> c){
        collection.forEach(c);
        return this;
    }

    public <G extends Collection<I>> Operator<G, I> copyTo(Supplier<G> s){
        G c = s.get();
        for(var element : collection)
            c.add(element);
        return modify(c);
    }

    public <G extends Collection<R>, R> Operator<G, R> convertTo(Supplier<G> s, Function<I, R> f){
        G c = s.get();
        for(var element : collection)
            c.add(f.apply(element));
        return modify(c);
    }

    public T get(){
        return collection;
    }
}
