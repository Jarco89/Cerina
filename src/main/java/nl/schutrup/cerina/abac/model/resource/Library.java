package nl.schutrup.cerina.abac.model.resource;

import java.util.ArrayList;

public class Library extends Resource {

    public Library() {
        super.setName("Library");
        resourceAttributes = new ArrayList<>();
        resourceAttributes.add(new ResourceAttribute("Library", "Amsterdam"));
    }
}
