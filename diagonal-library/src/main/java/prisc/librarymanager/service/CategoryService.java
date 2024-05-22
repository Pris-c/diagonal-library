package prisc.librarymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.librarymanager.model.volume.Category;
import prisc.librarymanager.repository.CategoryRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing category-related operations in the library.
 */
@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Processes a set of categories, retrieving their information from the database.
     * If a category is not found, it is saved first.
     *
     * @param categories Set of Category objects containing the categories' names.
     * @return Set of Category objects containing both the names and IDs of the categories.
     */
    public Set<Category> processCategories(Set<Category> categories){
        return  categories.stream().map(this::processCategory).collect(Collectors.toSet());
    }

    /**
     * Retrieves the Category witch name matches the informed substring
     *
     * @param categoryName substring representing the category name
     * @return Category correspondent to Category Name or null
     *
     */
    public List<Category> findByName(String categoryName) {
        return categoryRepository.findByNameContainingIgnoreCase(categoryName).orElse(null);
    }

    /**
     * Retrieves category's information from the database.
     * If the category is not found, it saves it first.
     *
     * @param category The Category object containing the category's name.
     * @return A Category object containing both the name and ID.
     */
    private Category processCategory(Category category){
        return categoryRepository.findByNameIgnoreCase(category.getName()).orElseGet(() -> categoryRepository.save(category));
    }

    /**
     * Retrieves the name of a category from the database based on the category's unique ID.
     *
     * @param categoryID The unique ID of the category.
     * @return A String containing the name of the category.
     */
    private String getName(UUID categoryID){
        Category category = categoryRepository.findById(categoryID).orElseThrow();
        return category.getName();
    }

}
