package prisc.librarymanager.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import prisc.librarymanager.model.Category;
import prisc.librarymanager.repository.CategoryRepository;
import prisc.librarymanager.util.CategoryCreator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for CategoryService")
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;
    @Mock
    CategoryRepository categoryRepositoryMock;


    @Test
    @DisplayName("processCategories with persisted Categories: Returns a Set of Categories whenSuccessful")
    void processCategories_PersistedCategories_ReturnsASetOfCategories_WhenSuccessful() {
        when(categoryRepositoryMock.findByNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(CategoryCreator.createCategory()));

        Set<Category> categories = categoryService.processCategories(CategoryCreator.createCategorySetToSave());
        Assertions.assertThat(categories).isNotNull().isNotEmpty();
        Assertions.assertThat(categories.size()).isEqualTo(1);

        verify(categoryRepositoryMock, times(2)).findByNameIgnoreCase(anyString());
        verifyNoMoreInteractions(categoryRepositoryMock);
    }

    @Test
    @DisplayName("processCategories with new categories: Returns a Set of Category (including ID) whenSuccessful")
    void processCategories_NewCategories_ReturnsASetOfCategoriesIncludingID_WhenSuccessful() {
        when(categoryRepositoryMock.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(categoryRepositoryMock.save(any(Category.class))).thenReturn(CategoryCreator.createCategory());

        Set<Category> categoriesReceivedSet = CategoryCreator.createCategorySetToSave();
        Set<Category> processedCategories = categoryService.processCategories(categoriesReceivedSet);
        Assertions.assertThat(processedCategories).isNotNull().isNotEmpty();
        Assertions.assertThat(processedCategories.size()).isEqualTo(1);

        verify(categoryRepositoryMock, times(categoriesReceivedSet.size())).findByNameIgnoreCase(anyString());
        verify(categoryRepositoryMock, times(categoriesReceivedSet.size())).save(any(Category.class));
        verifyNoMoreInteractions(categoryRepositoryMock);
    }

    @Test
    @DisplayName("findByName: Returns a List of Categories when successful")
    void findByName_ReturnsListOfCategories_WhenSuccessful() {
        when(categoryRepositoryMock.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(CategoryCreator.createCategorySet().stream().toList()));

        List<Category> categories = categoryService.findByName("Art");
        Assertions.assertThat(categories).isNotNull().isNotEmpty();
        Assertions.assertThat(categories.size()).isEqualTo(2);
        categories.forEach(a -> Assertions.assertThat(a.getCategoryId()).isNotNull());;
    }

    @Test
    @DisplayName("findByName: Returns null when no category is found")
    void findByName_ReturnsNull_WhenNoACategoryIsFound() {
        when(categoryRepositoryMock.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(Optional.empty());

        List<Category> categories = categoryService.findByName("Art");
        Assertions.assertThat(categories).isNull();
    }

}