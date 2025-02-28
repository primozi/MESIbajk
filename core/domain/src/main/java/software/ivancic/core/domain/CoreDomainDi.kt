package software.ivancic.core.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

@Module
@ComponentScan("software.ivancic.core.domain")
class CoreDomainDi

val coreDomainDi = module {
    single<CoroutineDispatchers> {
        CoroutineDispatchersImpl()
    }
}
