package pl.touk.sputnik.exec;

import net.sf.saxon.trans.Err;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import pl.touk.sputnik.TestEnvironment;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExternalProcessTest extends TestEnvironment{
    @Test
    void shouldCallExecutorFunction() {
        // Partie 1
        ProcessExecutor processExecutor = mock(ProcessExecutor.class);
        ProcessResult processResult = mock(ProcessResult.class);
        ExternalProcess externalProcess  = spy(ExternalProcess.class);
        String[] args = {"args1", "args2"};

        // Partie 2

        // ProcessExecutor
        lenient().when(processExecutor.command(args)).thenReturn(processExecutor);
        lenient().when(processExecutor.timeout(60, TimeUnit.SECONDS)).thenReturn(processExecutor);
        lenient().when(processExecutor.redirectError(any())).thenReturn(processExecutor);
        lenient().when(processExecutor.readOutput(true)).thenReturn(processExecutor);
        lenient().when(processResult.outputUTF8()).thenReturn("random");

        try {
            lenient().when(processExecutor.execute()).thenReturn(processResult);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ExternalProcess
        lenient().when(externalProcess.executor()).thenReturn(processExecutor);


        // Partie 3
        externalProcess.executeCommand(args);
        verify(externalProcess, times(1)).executor();
    }

    @Test
    void shouldCreateProcessExecutor() {
        ExternalProcess externalProcess = new ExternalProcess();
        assertThat(externalProcess.executor()).isExactlyInstanceOf(ProcessExecutor.class);
    }
}

