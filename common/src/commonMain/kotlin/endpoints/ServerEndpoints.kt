package endpoints

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ArraySerializer
import transferObjects.show.Show


@OptIn(ExperimentalSerializationApi::class)
val _getShows = Endpoint(
    url = "/shows",
    method = EndpointMethod.GET,
    serializer = ArraySerializer(Show.serializer()),
    queryKey = "shows"
)