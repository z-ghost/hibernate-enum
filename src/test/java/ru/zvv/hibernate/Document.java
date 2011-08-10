package ru.zvv.hibernate;

/**
 * @author Vitaly Zubchevskiy
 *         Created at 15:48 08.08.11
 */
public class Document
{
    private Integer id;
    private State state;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }
}
