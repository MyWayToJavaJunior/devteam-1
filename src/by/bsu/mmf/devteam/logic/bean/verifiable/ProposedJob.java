package by.bsu.mmf.devteam.logic.bean.verifiable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.2.0-beta
 */
public class ProposedJob {
    @NotNull
    @Size(min = 1)
    private String name;

    @Pattern(regexp = "Architect|Copywriter|Designer|Programmer|Tester")
    private String qualification;

    @NotNull
    @Min(value = 1)
    private int count;
}
