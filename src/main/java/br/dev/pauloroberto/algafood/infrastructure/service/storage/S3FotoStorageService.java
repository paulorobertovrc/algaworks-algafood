package br.dev.pauloroberto.algafood.infrastructure.service.storage;

import br.dev.pauloroberto.algafood.core.storage.StorageProperties;
import br.dev.pauloroberto.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AmazonS3 amazonS3;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    getCaminhoArquivo(novaFoto.getNomeArquivo()),
                    novaFoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para o serviço Amazon S3.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            amazonS3.deleteObject(storageProperties.getS3().getBucket(), getCaminhoArquivo(nomeArquivo));
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo no serviço Amazon S3.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), getCaminhoArquivo(nomeArquivo));

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}
