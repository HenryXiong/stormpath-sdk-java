package com.stormpath.sdk.impl.provider

import com.stormpath.sdk.impl.ds.InternalDataStore
import com.stormpath.sdk.impl.resource.DateProperty
import com.stormpath.sdk.impl.resource.ListProperty
import com.stormpath.sdk.impl.resource.StringProperty
import com.stormpath.sdk.provider.Provider
import com.stormpath.sdk.provider.TwitterProvider
import org.testng.annotations.Test

import static org.easymock.EasyMock.createStrictMock
import static org.testng.Assert.assertEquals
import static org.testng.Assert.assertTrue
/**
 * @since 1.3.0
 */
class DefaultTwitterProviderTest {

    @Test
    void testGetPropertyDescriptors() {

        def provider = new DefaultTwitterProvider(createStrictMock(InternalDataStore))

        def propertyDescriptors = provider.getPropertyDescriptors()

        assertTrue(propertyDescriptors.get("providerId") instanceof StringProperty)
        assertTrue(propertyDescriptors.get("createdAt") instanceof DateProperty)
        assertTrue(propertyDescriptors.get("modifiedAt") instanceof DateProperty)
        assertTrue(propertyDescriptors.get("clientId") instanceof StringProperty)
        assertTrue(propertyDescriptors.get("clientSecret") instanceof StringProperty)
        assertTrue(propertyDescriptors.get("scope") instanceof ListProperty)
        assertEquals(propertyDescriptors.size(), 7)

        assertTrue(Provider.isInstance(provider))
        assertTrue(TwitterProvider.isInstance(provider))
    }

    @Test
    void testMethods() {

        def properties = [href: "https://api.stormpath.com/v1/directories/iouertnw48ufsjnsDFSf/provider",
                          createdAt: "2013-10-01T23:38:55.000Z",
                          modifiedAt: "2013-10-02T23:38:55.000Z",
                          clientId: "613598318417022",
                          clientSecret: "c1ad951d45fdd0010c1c7d67c8f1d800",
                          scope: ["foo", "bar"]
        ]

        def internalDataStore = createStrictMock(InternalDataStore)
        def provider = new DefaultTwitterProvider(internalDataStore, properties)

        assertEquals(provider.getHref(), "https://api.stormpath.com/v1/directories/iouertnw48ufsjnsDFSf/provider")
        assertEquals(provider.getProviderId(), "twitter")
        assertEquals(provider.getCreatedAt().format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", TimeZone.getTimeZone("GMT")), "2013-10-01T23:38:55.000Z")
        assertEquals(provider.getModifiedAt().format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", TimeZone.getTimeZone("GMT")) , "2013-10-02T23:38:55.000Z")
        assertEquals(provider.getClientId(), "613598318417022")
        assertEquals(provider.getClientSecret(), "c1ad951d45fdd0010c1c7d67c8f1d800")
        assertEquals(provider.getScope(), ["foo", "bar"])

        provider.setClientId("999999999999")
        assertEquals(provider.getClientId(), "999999999999")
        provider.setClientSecret("AAAAA9999999")
        assertEquals(provider.getClientSecret(), "AAAAA9999999")
    }
}
