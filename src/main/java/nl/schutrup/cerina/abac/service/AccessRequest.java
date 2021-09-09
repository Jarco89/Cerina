package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;

import java.util.Objects;


public class AccessRequest {

    private Resource resource;
    private ServerResult serverResult;
    private Subject subject;
    private Operation operation;

    public AccessRequest(Resource resource, ServerResult serverResult, Subject subject, Operation operation) {
        this.resource = resource;
        this.serverResult = serverResult;
        this.subject = subject;
        this.operation = operation;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ServerResult getServerResult() {
        return serverResult;
    }

    public void setServerResult(ServerResult serverResult) {
        this.serverResult = serverResult;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessRequest that = (AccessRequest) o;
        return Objects.equals(resource, that.resource) &&
                Objects.equals(serverResult, that.serverResult) &&
                Objects.equals(subject, that.subject) &&
                operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, serverResult, subject, operation);
    }

    @Override
    public String toString() {
        return "AccessRequest{" +
                "resource=" + resource +
                ", serverResult=" + serverResult +
                ", subject=" + subject +
                ", operation=" + operation +
                '}';
    }
}
