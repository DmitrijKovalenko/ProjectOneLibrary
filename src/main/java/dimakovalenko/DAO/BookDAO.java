package dimakovalenko.DAO;
import dimakovalenko.models.Book;
import dimakovalenko.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book ", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title,author,year)VALUES (?,?,?)"
                , book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET title=? , author=? , year=? WHERE id=?", updateBook.getTitle(),
                updateBook.getAuthor(), updateBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    //Join имтаблицы Book и Person и получаем человека ,которому принадлежит книга с указанным id
    public Optional<Person> getBookOwner(int id) {
        //Выбираем все колонки таблицы Person  из объединенной таблицы
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id" +
                        " WHERE Book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    //Освобождает книгу(этот метод вызываеться когда человек возвращает книгу в библиотеку)
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id = ?", id);
    }

    //Назначает книгу (этот метод вызываеться когда человек берет книгу в библилтеке)
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", selectedPerson.getId(), id);
    }


}
