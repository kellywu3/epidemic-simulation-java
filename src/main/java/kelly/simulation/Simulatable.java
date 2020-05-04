package kelly.simulation;

import kelly.simulation.domain.Position;

public interface Simulatable extends Animatable {
    Position getPosition();

    void simulate();
}
