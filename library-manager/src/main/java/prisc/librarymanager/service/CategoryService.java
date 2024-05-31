package prisc.librarymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.librarymanager.model.Category;
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
     * Calls processCategory for each Category on the Set<>
     *
     * @param categories Set<Category> containing volume's categories' names.
     * @return Set<Category> containing both names and ids of volume's categories.
     */
    public Set<Category> processCategories(Set<Category> categories){
        return  categories.stream().map(this::processCategory).collect(Collectors.toSet());
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
     * Retrieves the name of the category from the database based on the category's unique ID.
     *
     * @param categoryID The unique ID of the category.
     * @return A String containing the name of the category.
     */
    private String getName(UUID categoryID){
        Category category = categoryRepository.findById(categoryID).orElseThrow();
        return category.getName();
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

}
