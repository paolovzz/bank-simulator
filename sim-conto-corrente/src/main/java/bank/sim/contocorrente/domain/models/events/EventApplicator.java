package bank.sim.contocorrente.domain.models.events;

public interface EventApplicator {
    
    public void apply(ContoCorrenteAperto event);
}
