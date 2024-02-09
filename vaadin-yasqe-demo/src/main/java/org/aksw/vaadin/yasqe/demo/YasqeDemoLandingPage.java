package org.aksw.vaadin.yasqe.demo;

import java.util.List;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Yasqe + Vaadin demos")
@Route("")
public class YasqeDemoLandingPage extends Composite<VerticalLayout> {
    private static final long serialVersionUID = 1L;

    private final Grid<Example> gridOfExamples = new Grid<>();

    public YasqeDemoLandingPage() {
        this.gridOfExamples
            .addColumn(new ComponentRenderer<>(example -> {
                final Anchor anchor = new Anchor(example.route(), example.name());

                final Span spDesc = new Span(example.desc());
                spDesc.getStyle().set("font-size", "90%");

                final VerticalLayout vl = new VerticalLayout(anchor, spDesc);
                vl.setSpacing(false);
                return vl;
            }))
            .setHeader("Available demos");

        this.gridOfExamples.setSizeFull();
        this.gridOfExamples.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);

        this.getContent().add(this.gridOfExamples);
        this.getContent().setHeightFull();
    }

    @Override
    protected void onAttach(final AttachEvent attachEvent) {
        this.gridOfExamples.setItems(List.of(
            new Example(
                YasqeDemoSimple.NAV,
                "Simple",
                "Showcasing the simplest form of using the API"
            )
        ));
    }

    record Example(String route, String name, String desc) {
    }
}
