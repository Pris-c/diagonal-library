package prisc.diagonallibrary.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GoogleApiResponse implements Serializable {

    List<Item> items;

}
