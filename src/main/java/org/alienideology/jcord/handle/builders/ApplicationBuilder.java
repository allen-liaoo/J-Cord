package org.alienideology.jcord.handle.builders;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.internal.object.client.app.Application;

/**
 * ApplicationBuilder - A builder for creating an {@link IApplication}. Used by {@link org.alienideology.jcord.handle.managers.IClientManager}.
 *
 * @author AlienIdeology
 * @since 0.1.4
 */
public final class ApplicationBuilder implements Buildable<ApplicationBuilder, IApplication> {

    private String name;
    private Icon icon;
    private String description;

    public ApplicationBuilder() {
        clear();
    }

    /**
     * Build an application.
     *
     * @return The application built, used in {@link org.alienideology.jcord.handle.managers.IClientManager#createApplication(IApplication)}.
     */
    @Override
    public IApplication build() {
        return new Application(null, null)
                .setName(name)
                .setIcon(icon == null || icon == Icon.DEFAULT_ICON ? null : (String) icon.getData())
                .setDescription(description);
    }

    @Override
    public ApplicationBuilder clear() {
        name = null;
        icon = null;
        description = null;
        return this;
    }

    /**
     * Set the name of this application.
     *
     * @exception IllegalArgumentException
     *          If the name is not valid. See {@link IApplication#isValidName(String)}.
     *
     * @param name The string name.
     * @return ApplicationBuilder for chaining.
     */
    public ApplicationBuilder setName(String name) {
        if (!IApplication.isValidName(name)) {
            throw new IllegalArgumentException("Invalid application name!");
        }
        this.name = name;
        return this;
    }

    /**
     * Set the icon of this application.
     *
     * @param icon The image file.
     * @return ApplicationBuilder for chaining.
     */
    public ApplicationBuilder setIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Set the description of this application.
     *
     * @exception IllegalArgumentException
     *          If the description is not valid. See {@link IApplication#isValidDescription(String)}.
     *
     * @param description The description.
     * @return ApplicationBuilder for chaining.
     */
    public ApplicationBuilder setDescription(String description) {
        if (!IApplication.isValidDescription(description)) {
            throw new IllegalArgumentException("Invalid application description!");
        }
        this.description = description;
        return this;
    }

}
