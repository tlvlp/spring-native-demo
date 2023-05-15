import asyncio
import aiohttp


async def fetch_data(session, url, counter):
    async with session.post(url, json={'appendWith': counter}) as response:
        return await response.json()


async def send_requests(url, num_requests):
    async with aiohttp.ClientSession() as session:
        tasks = []
        for counter in range(1, num_requests):
            task = asyncio.ensure_future(fetch_data(session, url, counter))
            tasks.append(task)

        results = await asyncio.gather(*tasks)
        return results

async def main():
    url = 'http://localhost:8080/append'
    num_requests = 5

    results = await send_requests(url, num_requests)
    print(results)

if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())
