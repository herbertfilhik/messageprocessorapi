package com.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String origem;
    private String destino;
    private String courierId;
    private String status;
    private Long eventTime;
    private String originId;
    private String destinationId; // Adicione a propriedade destinationId

    // Construtores

    public Rota() {
        // Construtor padrão vazio
    }

    public Rota(String origem, String destino, String courierId, String status, Long eventTime, String originId, String destinationId) {
        this.origem = origem;
        this.destino = destino;
        this.courierId = courierId;
        this.status = status;
        this.eventTime = eventTime;
        this.originId = originId;
        this.destinationId = destinationId; // Inicialize a propriedade destinationId
    }

    // Getters e Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getCourierId() {
		return courierId;
	}

	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEventTime() {
		return eventTime;
	}

	public void setEventTime(Long eventTime) {
		this.eventTime = eventTime;
	}

	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}
    
    // ... (métodos getters e setters para destinationId)

    // Outros métodos, se necessário    
}
